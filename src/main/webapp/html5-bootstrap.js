$(function () {

    var allCapitals;

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
                        populatePickList(capitals, selectedCapitals);
                        initPickList();
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

    var populatePickList = function (capitals, selectedCapitals) {
        allCapitals = capitals;
        var sourceList = $('#myPickList .source')
        var targetList = $('#myPickList .target')
        $.each(selectedCapitals, function (index, capital) {
            targetList.append($("<li/>").data('key', capital.name).data('object', capital).text(capital.name));
        });
        var unselectedCapitals = $.grep(capitals, function (capital) {
            return ($.grep(selectedCapitals,function (c) {
                return c.name === capital.name
            }).length === 0);
        });
        $.each(unselectedCapitals, function (index, capital) {
            sourceList.append($("<li/>").data('key', capital.name).data('object', capital).text(capital.name));
        });
    }

    var updatePickList = function (selectedCapitals) {
        // retrieve the list elements from the targetList
        var sourceList = $('#myPickList .source');
        var targetList = $('#myPickList .target');
        var tempList = $("<ol />");
        tempList.append(sourceList.find('li').detach());
        tempList.append(targetList.find('li').detach());

        // put the selected list elements back in the targetList in the selected order
        $.each(selectedCapitals, function(index, capital) {
            var listElement = $.grep(tempList.find('li'), function (li) {
                return $(li).data('object').name === capital.name;
            });
            targetList.append($(listElement).detach());
        });

        // put the non-selected list elements back in the sourceList in the original order
        $.each(allCapitals, function(index, capital) {
            var listElement = $.grep(tempList.find('li'), function (li) {
                return $(li).data('object').name === capital.name;
            });
            if (listElement.length > 0) {
                sourceList.append($(listElement).detach());
            }
        });
    }

    var initPickList = function () {
        $('#myPickList').pickList();
    }

    var submit = function () {
        var selectedCapitals = [];
        $('#myPickList .target').find(".ui-selectee").each(function (index, li) {
            selectedCapitals.push($(li).data('object'));
        });
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
                updatePickList(selectedCapitals);

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