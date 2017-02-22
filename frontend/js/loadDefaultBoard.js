var loadDefaultBoard = function (callback) {
    $.get( "/backend/getBoard?id=1", function( board ) {
        callback(board);
    });
}
