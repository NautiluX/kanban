var App = function() {
    this.DEFAULT_BOARD_ID = 1;
    this.readWrite = $.url('?rw');
    this.boardId = ~~$.url('?board');
    if (!this.boardId) {
        this.boardId = this.DEFAULT_BOARD_ID;
    }
}

App.prototype.loadDefaultBoard = function () {
    this.loadBoard(this.boardId);
};

App.prototype.loadBoard = function (boardId) {
    var readWrite = $.url('?rw');
    if (readWrite !== 'true') {
        model.loadBoardWorldReadable(boardId);
    } else {
        model.loadBoard(boardId);
    }
};
