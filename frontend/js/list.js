var adjustment;
$(function  () {
    $("ol.example").sortable({
        group: 'example',
        pullPlaceholder: false,
        exclude: '.newCard',
        // animation on drop
        onDrop: function  ($item, container, _super) {
            var $clonedItem = $('<li/>').css({height: 0});
            $item.before($clonedItem);
            $clonedItem.animate({'height': $item.height()});

            $item.animate($clonedItem.position(), function  () {
                $clonedItem.detach();
                _super($item, container);
            });
        },

        // set $item relative to cursor position
        onDragStart: function ($item, container, _super) {
            var offset = $item.offset(),
            pointer = container.rootGroup.pointer;

            adjustment = {
                left: pointer.left - offset.left,
                top: pointer.top - offset.top
            };

            _super($item, container);
        },
        onDrag: function ($item, position) {
            $item.css({
                left: position.left - adjustment.left,
                top: position.top - adjustment.top
            });
        }
    });



    $('textarea.newCard').on("keydown", function (e) {
      if (e.which == 13) {
         $("<li>").insertBefore($(this.parentNode.parentNode).find("li.newCard")).append(this.value);
          this.value = "";
        }
    });
});
