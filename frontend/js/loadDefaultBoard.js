var DEFAULT_BOARD_ID = 1;
var loadDefaultBoard = function () {
    var readWrite = $.url('?rw');
    if (readWrite !== 'true') {
        model.loadBoardWorldReadable(DEFAULT_BOARD_ID);
    } else {
        model.loadBoard(DEFAULT_BOARD_ID);
    }
};
