$( document ).ready(function() {
    loadDefaultBoard();
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
            $('#lane_'+lane.id+'_list').append('<li class="example">' + card.content + '</li>');
        });
        $('#lane_'+lane.id+'_list').append(newCardListItem);
    });
};
