var Model = function() {
    var selectedBoard;
    var tag = null;
    var readWrite = false;
};

Model.prototype.updateModel = function(callback) {
    this.loadBoard(this.selectedBoard.id, callback);
};

Model.prototype.loadBoard = function(id, callback) {
    this.readWrite = true;
    $.get( model.generateBoardURL("/backend/getBoard", id), function( board ) {
        model.updateSelectedBoard(board, callback);
    });
};

Model.prototype.isReadWrite = function () {
    return this.readWrite;
};

Model.prototype.loadBoardWorldReadable = function(id, callback) {
    $.get( model.generateBoardURL("/backend/getBoardWorldReadable", id), function( board ) {
        model.updateSelectedBoard(board, callback);
    });
};

Model.prototype.generateBoardURL = function (url, id) {
    var fullUrl = url;
    fullUrl += "?id=" + id;
    if (this.tag)
        fullUrl += "&tag=" + this.tag;
    return fullUrl;
};

Model.prototype.updateSelectedBoard = function(board, callback) {
    this.selectedBoard = board;
    callback();
};

Model.prototype.createCard = function(laneId, content, callback) {
    $.post("/backend/newCard", 
        {"boardId": this.selectedBoard.id, 
         "laneId": laneId,
         "content": content},
        function (data) {
            model.updateModel(callback);
        });
};

Model.prototype.getSelectedBoard = function () {
    return this.selectedBoard;
};

Model.prototype.hasPermission = function (permission) {
    return this.selectedBoard.permissions.indexOf(permission) >= 0;
};

Model.prototype.moveCard = function(cardId, newLaneId, callback) {
    $.post("/backend/moveCard", 
        {"boardId": this.selectedBoard.id,
         "cardId": cardId,
         "newLaneId": newLaneId},
        function (data) {
            model.updateModel(callback);
        });
};

Model.prototype.deleteCard = function(card, callback) {
    $.post("/backend/deleteCard", 
        { "cardId": card.id },
        function (data) {
            model.updateModel(callback);
        });
};

Model.prototype.getTag = function () {
    return this.tag?this.tag:'';
};

Model.prototype.setTag = function (tag) {
    this.tag = tag;
};

Model.prototype.getCard = function (cardId, callback) {
    this.selectedBoard.lanes.forEach(function (lane) {
        lane.cards.forEach(function (card) {
            if (card.id === cardId) {
                callback(card);
                return;
            }
        });
    });
};

Model.prototype.updateCard = function (card, callback) {
    $.post("/backend/updateCard",
        {"boardId": card.boardId, 
         "cardId": card.id,
         "content": card.content},
        function (data) {
            model.updateModel(callback);
        });
};

var model = new Model();
