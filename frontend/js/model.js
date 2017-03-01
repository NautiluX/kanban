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

Model.prototype.updateSelectedBoard = function(board) {
    selectedBoard = board;
    renderBoard(board);
};

var model = new Model();
