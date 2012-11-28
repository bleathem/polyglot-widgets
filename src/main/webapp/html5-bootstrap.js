$(function () {

    var bootstrap = function () {
        fetchCapitals();
        $('#submit').click(submit);
    }

    var fetchCapitals = function () {
        $.ajax('rs/capitals/all', {
            dataType:'json',
            type:'GET',
            success:function (capitals) {
                console.log("success, capitals: " + capitals);
                $.ajax('rs/capitals/selected', {
                    dataType:'json',
                    type:'GET',
                    success:function (selectedCapitals) {
                        console.log("success, selectedCapitals: " + selectedCapitals);
                        initPickList();
                        $('#myPickList').pickListGlue("populatePickList", capitals, selectedCapitals);
                        subscribe();
                    }
                }).error(function () {
                        console.log("error retrieving all capitals");
                    });
            }
        }).error(function () {
                console.log("error retrieving all capitals");
            });
    }

    var initPickList = function () {
        $('#myPickList').pickList();
        $('#myPickList').pickListGlue();
    }

    var submit = function () {
        var selectedCapitals = $('#myPickList').pickListGlue("getSelectedItems");

        $.ajax('rs/capitals/selected', {
            contentType:"application/json",
            dataType:'json',
            type:'POST',
            data:JSON.stringify(selectedCapitals),
            success:function (data) {
                console.log("Ajax capital post success");
            }
        }).error(function (error) {
                var errorMessage = $.parseJSON(error.responseText);
                $.each(errorMessage, function (index, message) {
                    console.log("Ajax capital post error: " + message);
                });
            });
    }

    var subscribe = function () {
        var request = { url:'subscribe/selectedCapitals',
            contentType:"application/json",
            logLevel:'debug',
            transport:'long-polling',
            onMessage:function (response) {
                console.log(response.responseBody);
                var selectedCapitals = JSON.parse(response.responseBody);
                $('#myPickList').pickListGlue("updatePickList", selectedCapitals);
            },
            onOpen:function (response) {
                console.log('Atmosphere connected using ' + response.transport);
            },
            onReconnect:function (request, response) {
                console.log("reconnecting");
            },
            onError: function(response) {
                console.log("socket or server problem")
            }
        };

        var socket = $.atmosphere;
        var subSocket = socket.subscribe(request);

    }

    bootstrap();

})
;