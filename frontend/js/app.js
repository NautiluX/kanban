var App = function() {
    this.DEFAULT_BOARD_ID = 1;
    this.readWrite = $.url('?rw');
    this.boardId = ~~$.url('?board');
    if (!this.boardId) {
        this.boardId = this.DEFAULT_BOARD_ID;
    }
};

App.prototype.loadDefaultBoard = function () {
    this.loadBoard(this.boardId);
};

App.prototype.loadBoard = function (boardId) {
    var readWrite = $.url('?rw');
    model.setTag($.url('?tag'));
    model.setShowArchivedCards($.url('?showArchivedCards') === 'true');
    this.loadBoardReadmode(boardId, readWrite);
};

App.prototype.onLoaded = function() {
    this.clearMenubar();
    this.initializeMenubar();
    renderBoard(model.getSelectedBoard());
    this.updateTagFilter();
    this.updateTitle();
}

App.prototype.loadBoardReadmode = function (boardId, readWrite) {
    var self = this;
    var callback = function() {self.onLoaded()};
    if (readWrite !== 'true') {
        model.loadBoardWorldReadable(boardId, callback);
    } else {
        model.loadBoard(boardId, callback);
    }
};

App.prototype.updateTagFilter = function() {
    if (model.getTag())
        $("#currentTag").html('Current Tag filter: ' + this.generateParamLink('remove_tag', 'Remove Tag filter', '#'+model.getTag(), {tag: ''}));
};

App.prototype.updateTitle = function() {
    document.title = model.getSelectedBoard().name + ' - YuKan';
};

App.prototype.clearMenubar = function() {
    $('#menubar').html('');
};

App.prototype.initializeMenubar = function() {
    this.createMenuItem('menu', 'Menu');

    this.createMenuItem('currentTag', 'All Cards Shown');

    var archivedMode = model.getShowArchivedCards()?'Hide':'Show';
    this.createMenuItem('showArchivedCards', this.generateParamLink('showArchivedCards_link', archivedMode + ' Archived Cards', archivedMode + ' Archived Cards', {showArchivedCards: !model.getShowArchivedCards()}));

    if (!model.hasPermission("CONTRIBUTE"))
        this.createMenuItem('login', this.generateParamLink('login_link', 'Log in', 'Log in', {rw: true}));
};

App.prototype.hasPermission = function (permission) {
    return model.hasPermission(permission);
};

App.prototype.createMenuItem = function(id, html) {
    $('#menubar').append($('<div class="menuitem" id="' + id + '">' + html + '</div>'));
};

App.prototype.generateParamLink = function(id, title, text, change) {
    var href = '/';
    var rwValue = model.isReadWrite();
    if (change.hasOwnProperty('rw')) {
        rwValue = change.rw;
    }
    href += '?rw=' + (rwValue?'true':'false');

    var tagValue = model.getTag();
    if (change.hasOwnProperty('tag')) {
        tagValue = change.tag;
    }
    href += '&tag=' + tagValue;

    var archivedValue = model.getShowArchivedCards();
    if (change.hasOwnProperty('showArchivedCards')) {
        archivedValue = change.showArchivedCards;
    }
    href += '&showArchivedCards=' + archivedValue;

    var link = '<a href="' + href + '" id="' + id + '" title="' + title + '">' + text + '</a>';
    return link;
};

App.prototype.moveCard = function(cardId, newLaneId) {
    var self = this;
    var callback = function() {self.onLoaded()};
    model.moveCard(cardId, newLaneId, callback);
};

App.prototype.createCard = function(laneId, content) {
    var self = this;
    var callback = function() {self.onLoaded()};
    model.createCard(laneId, content, callback);
};

App.prototype.deleteCard = function(card) {
    var self = this;
    var callback = function() {self.onLoaded()};
    model.deleteCard(card, callback);
};

App.prototype.showCard = function(cardId) {
    var card = model.getCard(~~cardId, renderCardPopup);
};

App.prototype.updateCard = function(card) {
    var self = this;
    var callback = function() {self.onLoaded()};
    model.updateCard(card, callback);
    hidePopup();
};
