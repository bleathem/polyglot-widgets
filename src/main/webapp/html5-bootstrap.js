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
                        populatePickList(capitals, selectedCapitals);
                        initPickList();
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
        bootstrap();

});