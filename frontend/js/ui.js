var app;
$( document ).ready(function() {
    app = new App();
    app.loadDefaultBoard();
    registerEvents();
});

var renderBoard = function (board) {
    setBoardTitle(board.name);
    createBoardLanes(board);
    initSortable();
};

var setBoardTitle = function (boardName) {
    if (boardName)
        $("#boardTitle").html(boardName);
};

var createBoardLanes = function (board) {
    if (board.lanes && board.lanes.length > 0) {
        removeLanes();
        createLanes(board.lanes);
    }
};

var removeLanes = function () {
    $(SORTABLE_SELECTOR).sortable("destroy")    
   $("#lanes").html(""); 
};

var newCardListItem='<li class="newCard"> <textarea class="newCard" placeholder="+ new Card"></textarea> </li>';
var createLanes = function (lanes) {
    lanes.forEach(function (lane) {
        $('#lanes').append('<span class="lane noscrollbar" id="lane_' + lane.id + '" lane_id="' + lane.id + '">');
        $('#lane_'+lane.id).append('<h2>'+lane.title+'</h2>').append('<ol class="example" id="lane_' + lane.id + '_list">');
        lane.cards.forEach(function (card) {
            renderCard(lane, card);
        });
        if (model.hasPermission("CONTRIBUTE")) {
            $('#lane_'+lane.id+'_list').append(newCardListItem);
        }
    });
};

var renderCard = function (lane, card) {
    var cardHtml = renderCardListItem(card);
    var cardContent = renderCardContent(card);
    $('#lane_'+lane.id+'_list').append(cardHtml);
    $('#card_' + card.id).append(cardContent);
    if (model.hasPermission("CONTRIBUTE") && !card.isArchived) {
        var deleteHtml = renderCardDeleteButton(card);
        $('#card_' + card.id).append(deleteHtml);
    }
};

var renderCardListItem = function (card) {
    var cardHtml = '<li class="example' + (card.isArchived?' archived':'') + '" card_id="' + card.id + '" id="card_' + card.id + '">';
    cardHtml += '</li>';
    return cardHtml;
};
var renderCardContent = function (card) {
    cardHtml = '<div class="cardContent">';
    cardHtml += highlightTags(card.content);
    cardHtml += '</div>';
    return cardHtml;
};
var highlightTags = function (content) {
    var highlightedContent = content;
    highlightedContent = highlightedContent.replace(/(#(.+?))(\s|#|$)/g, app.generateParamLink('', 'Filter by Label #$2', '#$2', {tag: '$2'}) + '$3');
    return highlightedContent;
};

var renderCardDeleteButton = function (card) {
    deleteHtml = '<button class="icon">';
    deleteHtml += '<i class="icon material-icons" card_id="' + card.id + '" id="delete_card_' + card.id + '">delete</i>';
    deleteHtml += '</button>';
    var deleteButton = $(deleteHtml);
    deleteButton.on("click", function (e) {
        app.deleteCard(card);
        return false;
    });
    return deleteButton;
};

var renderCardPopup = function (card) {
    var popupHtml = '';
    popupHtml += renderUpdateHeader(card);
    popupHtml += renderUpdateTextArea(card);
    popupHtml += renderUpdateButton(card);
    $("#popup").html(popupHtml);
    $("#popup").show();
    $("#popup").focus();
    $("#updateCard").on("click", function(){
        card.content = $("#updateCard_area").val();
        app.updateCard(card);
    });
};

var renderUpdateHeader = function (card) {
    var html = '<h2>Show & Update Card</h2>';
    return html;
}

var renderUpdateTextArea = function (card) {
    var html = null;
    if (app.hasPermission("CONTRIBUTE")) {
        html =  '<div class="cardInput"><textarea id="updateCard_area">' + card.content + '</textarea></div>';
    } else {
        html = card.content;
    }

    return html;
};

var renderUpdateButton = function (card) {
    var buttonHtml = '<button id="updateCard" class="icon done"><i class="material-icons icon">done</i></button>';
    return buttonHtml;
};

var registerEvents = function() {
    registerPopupUnfocus();
};

var registerPopupUnfocus = function() {
    $("#popup").on("keydown", function (e) {
      if (e.which == 27) {
          hidePopupIfUnselected();
          return false;
        }
    });
    $("#popup").on("blur", function(e) {
        hidePopupIfUnselected();
    });
};

var hidePopup = function() {
    $("#popup").hide();
};

var hidePopupIfUnselected = function () {
    setTimeout(function() {
        if (!childFocused($("#popup"))) {
            hidePopup();
        }
    }, 100);
};

var childFocused = function (elem) {
    var childHasFocus = false;
    elem.children().each(function(i){
        if($(this).is(":focus")) {
            childHasFocus = true;
        } else if (childFocused($(this))) {
            childHasFocus = true;
        }
    });
    return childHasFocus;
}
