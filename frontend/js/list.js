var adjustment;
var SORTABLE_SELECTOR="ol.example";
var initSortable = function () {
    $(SORTABLE_SELECTOR).sortable({
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
                moveCard($item);
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
    registerNewCardEvent();
}

var registerNewCardEvent = function() {
    $('textarea.newCard').on("keydown", function (e) {
      if (e.which == 13) {
          createCard(getLane(this.parentNode), this.value);
         $("<li>").insertBefore($(this.parentNode.parentNode).find("li.newCard")).append(this.value);
          this.value = "";
          return false;
        }
    });
};

var moveCard = function(card) {
    var lane = getLane(card);
    model.moveCard($(card).attr("card_id"), $(lane).attr("lane_id"));
}

var createCard = function(lane, content) {
    var laneId = $(lane).attr("lane_id");
    model.createCard(laneId, content);
};

var getLane = function(card) {
    return $(card).parent().parent()
}
