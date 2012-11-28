(function ($) {

    $.widget('rf.pickListGlue', {

        options: {
            componentId: null
        },

        _create: function() {
            var self = this;
            var componentId = this.options.componentId === null ? $(this.element).attr('id') : this.options.componentId;
            this.component = $(document.getElementById(componentId));
        },

        populatePickList: function (items, selectedItems) {
            var sourceList = this.component.find(' .source');
            var targetList = this.component.find(' .target');
            $.each(selectedItems, function (index, capital) {
                targetList.append($('<li class="ui-selectee"/>').data('key', capital.name).data('object', capital).text(capital.name));
            });
            var unselectedItems = $.grep(items, function (capital) {
                return ($.grep(selectedItems,function (c) {
                    return c.name === capital.name
                }).length === 0);
            });
            $.each(unselectedItems, function (index, capital) {
                sourceList.append($('<li class="ui-selectee"/>').data('key', capital.name).data('object', capital).text(capital.name));
            });
        },

        updatePickList: function (selectedItems) {
            // retrieve the list elements from the targetList
            var sourceList = $('#myPickList .source');
            var targetList = $('#myPickList .target');
            sourceList.prepend(targetList.find('li').detach());

            // put the selected list elements back in the targetList in the selected order
            $.each(selectedItems, function(index, capital) {
                var listElement = $.grep(sourceList.find('li'), function (li) {
                    return $(li).data('object').name === capital.name;
                });
                targetList.append($(listElement).detach());
            });
        },

        getSelectedItems: function() {
            var selectedItems = [];
            $('#myPickList .target').find(".ui-selectee").each(function (index, li) {
                selectedItems.push($(li).data('object'));
            });
            return selectedItems;
        }

    });

}(jQuery));
