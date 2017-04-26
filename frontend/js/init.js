var app;
$( document ).ready(function() {
    app = new App();
    app.loadDefaultBoard();
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
        $('#lanes').append('<span class="lane" id="lane_' + lane.id + '" lane_id="' + lane.id + '">');
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
    if (model.hasPermission("CONTRIBUTE")) {
        var deleteHtml = renderCardDeleteButton(card);
        $('#card_' + card.id).append(deleteHtml);
    }
};

var renderCardListItem = function (card) {
    var cardHtml = '<li class="example" card_id="' + card.id + '" id="card_' + card.id + '">';
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
    deleteHtml = '<button class="deleteCard">';
    deleteHtml += '<i class="deleteCard material-icons" card_id="' + card.id + '" id="delete_card_' + card.id + '">delete</i>';
    deleteHtml += '</button>';
    var deleteButton = $(deleteHtml);
    deleteButton.on("click", function (e) {app.deleteCard(card);});
    return deleteButton;
}
