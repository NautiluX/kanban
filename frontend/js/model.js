var Model = function() {
    var selectedBoard;
    var tag = null;
};

Model.prototype.updateModel = function() {
    this.loadBoard(selectedBoard.id);
};

Model.prototype.loadBoard = function(id) {
    $.get( model.generateBoardURL("/backend/getBoard", id), function( board ) {
        model.updateSelectedBoard(board);
    });
};

Model.prototype.loadBoardWorldReadable = function(id) {
    $.get( model.generateBoardURL("/backend/getBoardWorldReadable", id), function( board ) {
        model.updateSelectedBoard(board);
    });
};

Model.prototype.generateBoardURL = function (url, id) {
    var fullUrl = url;
    fullUrl += "?id=" + id;
    if (this.tag)
        fullUrl += "&tag=" + this.tag;
    return fullUrl;
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

Model.prototype.setTag = function (tag) {
    this.tag = tag;
}

var model = new Model();
