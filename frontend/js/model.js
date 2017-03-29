var Model = function() {
    var selectedBoard;
};
Model.prototype.updateModel = function() {
    this.loadBoard(selectedBoard.id);
};

Model.prototype.loadBoard = function(id) {
    $.get( "/backend/getBoard?id="+id, function( board ) {
        model.updateSelectedBoard(board);
    });
};

Model.prototype.loadBoardWorldReadable = function(id) {
    $.get( "/backend/getBoardWorldReadable?id="+id, function( board ) {
        model.updateSelectedBoard(board);
    });
};

Model.prototype.updateSelectedBoard = function(board) {
    selectedBoard = board;
    renderBoard(board);
};

Model.prototype.createCard = function(laneId, content) {
    $.post("/backend/newCard", 
        {"boardId": selectedBoard.id, 
         "laneId": laneId,
         "content": content},
        function (data) {
            model.updateModel();
        });
};

Model.prototype.moveCard = function(cardId, newLaneId) {
    $.post("/backend/moveCard", 
        { "cardId": cardId,
         "newLaneId": newLaneId},
        function (data) {
            model.updateModel();
        });
};

Model.prototype.deleteCard = function(card) {
    $.post("/backend/deleteCard", 
        { "cardId": card.id },
        function (data) {
            model.updateModel();
        });
}

var model = new Model();
