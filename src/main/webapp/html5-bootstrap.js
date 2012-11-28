$(function () {

    var bootstrap = function () {
        fetchCapitals();
        $('#submit').click(submit);
    }

    var fetchCapitals = function () {
        $.ajax('rs/capitals', {
            dataType:'json',
            type:'GET',
            success:function (capitals) {
                log("success, capitals: " + capitals);
                $.ajax('rs/capitals?filter=selected', {
                    dataType:'json',
                    type:'GET',
                    success:function (selectedCapitals) {
                        log("success, selectedCapitals: " + selectedCapitals);
                        initPickList();
                        $('#myPickList').pickListGlue("populatePickList", capitals, selectedCapitals);
                        subscribe();
                    }
                }).error(function () {
                        log("error retrieving all capitals");
                    });
            }
        }).error(function () {
                log("error retrieving all capitals");
            });
    }

    var initPickList = function () {
        $('#myPickList').pickList();
        $('#myPickList').pickListGlue();
    }

    var submit = function () {
        var selectedCapitals = $('#myPickList').pickListGlue("getSelectedItems");

        $.ajax('rs/capitals', {
            contentType:"application/json",
            dataType:'json',
            type:'PUT',
            data:JSON.stringify(selectedCapitals),
            success:function (data) {
                log("Ajax capital post success");
            }
        }).error(function (error) {
                var errorMessage = $.parseJSON(error.responseText);
                $.each(errorMessage, function (index, message) {
                    log("Ajax capital post error: " + message);
                });
            });
    }

    var subscribe = function () {
        var request = { url:'subscribe/selectedCapitals',
            contentType:"application/json",
            logLevel:'debug',
            transport:'long-polling',
            onMessage:function (response) {
                log(response.responseBody);
                var selectedCapitals = JSON.parse(response.responseBody);
                $('#myPickList').pickListGlue("updatePickList", selectedCapitals);
            },
            onOpen:function (response) {
                log('Atmosphere connected using ' + response.transport);
            },
            onReconnect:function (request, response) {
                log("reconnecting");
            },
            onError: function(response) {
                log("socket or server problem")
            }
        };

        var socket = $.atmosphere;
        var subSocket = socket.subscribe(request);

    }

    bootstrap();

})
;