$(function () {
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
                    var sourceList = $('#myPickList .source')
                    var targetList = $('#myPickList .target')
                    $.each(selectedCapitals, function(index, capital) {
                        targetList.append($("<li/>").data('key', capital.name).text(capital.name));
                    });
                    var unselectedCapitals = $.grep(capitals, function(capital) {
                        return ($.grep(selectedCapitals, function(c) {return c.name === capital.name}).length === 0);
                    });
                    $.each(unselectedCapitals, function(index, capital) {
                        sourceList.append($("<li/>").data('key', capital.name).text(capital.name));
                    });
                    init();
                }
            })
                .error(function () {
                    console.log("error retrieving all capitals");
                });            var sourceList = $('#myPickList .source')
        }
    })
        .error(function () {
            console.log("error retrieving all capitals");
        });

    var init = function() {
        var $pickList = $(document.getElementById('myPickList'));
        $pickList.pickList();
    }

});