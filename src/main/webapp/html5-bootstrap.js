$(function () {

    var bootstrap = function () {
        fetchCapitals();
        $('#submit').click(submit);
    }

    var capitalsPipe = AeroGear.Pipeline({
        name:"capitals",
        settings:{
            baseURL:'/rs/'
        }
    }).pipes['capitals'];

    var fetchCapitals = function () {
        capitalsPipe.read({
            success:function (capitals) {
                log("success, capitals: " + capitals);
                capitalsPipe.read({
                    query:{filter:'selected'},
                    success:function (selectedCapitals) {
                        log("success, selectedCapitals: " + selectedCapitals);
                        initPickList();
                        $('#myPickList').pickListGlue("populatePickList", capitals, selectedCapitals);
                        subscribe();
                    },
                    error:function () {
                        log("Error: retrieving selected capitals");
                    }
                });
            },
            error:function () {
                log("Error: retrieving all capitals");
            }
        });
    };


    var initPickList = function () {
        $('#myPickList').pickList();
        $('#myPickList').pickListGlue();
    }

    var submit = function () {
        var selectedCapitals = $('#myPickList').pickListGlue("getSelectedItems");

        capitalsPipe.save(
            selectedCapitals,
            {
                success:function () {
                    log("Success: submitting selected capitals ");
                },
                error: function () {
                    log("Error: submitting selected capitals");
                }
            }
        );
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
            onError:function (response) {
                log("socket or server problem")
            }
        };

        var socket = $.atmosphere;
        var subSocket = socket.subscribe(request);

    }

    bootstrap();

})
;