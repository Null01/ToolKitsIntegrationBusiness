'use strict';

var app = {}; // create namespace for our app

//--------------
// Models
//--------------
app.Todo = Backbone.Model.extend({
    defaults: {
        title: '',
        type: '',
        length: 0,
        completed: false
    },
    toggle: function () {
        this.save({completed: !this.get('completed')});
    }
});

//--------------
// Collections
//--------------
app.TodoList = Backbone.Collection.extend({
    model: app.Todo,
    localStorage: new Store("backbone-store")
});

// instance of the Collection
app.todoList = new app.TodoList();

//---------------------------------------------------------------------
// View canvas
//---------------------------------------------------------------------
app.TodoView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#canvas').html()),
    render: function () {
        this.$el.html(this.template(this.model.toJSON()));
        this.input = this.$('.edit');
        return this; // enable chained calls
    },
    initialize: function () {
        this.model.on('change', this.render, this);
        this.model.on('destroy', this.remove, this); // remove: Convenience Backbone's function for removing the view from the DOM.
    },
    events: {
        'dblclick label': 'edit',
        'keypress .edit': 'updateOnEnter',
        'click .toggle': 'toggleCompleted',
        'click .destroy': 'destroy'
    },
    edit: function () {
        this.$el.addClass('editing');
        this.input[0].focus();
    },
    close: function () {
        var name = this.input[0].value.trim();
        var size = this.input[1].value.trim();
        if (name) {
            this.model.save({
                title: name,
                length: size
            });
        }
        this.$el.removeClass('editing');
    },
    updateOnEnter: function (e) {
        if (e.which == 13) {
            this.close();
        }
    },
    toggleCompleted: function () {
        this.model.toggle();
    },
    destroy: function () {
        this.model.destroy();
    }
});

//---------------------------------------------------------------------
// View Body
//---------------------------------------------------------------------
app.AppView = Backbone.View.extend({
    el: '#main',
    initialize: function () {
        this.input = this.$('#name-field');
        this.inputType = this.$('#type-field');
        this.inputLength = this.$('#length-field');
        app.todoList.on('add', this.addAll, this);
        app.todoList.on('reset', this.addAll, this);
        app.todoList.fetch(); // Loads list from local storage
    },

    events: {
        'keypress #name-field': 'saveItem',
        'keypress #length-field': 'saveItem'
                // 'change #type-field': ''
    },

    //-------------------------------
    // Functions of view
    //-------------------------------
    saveItem: function (e) {
        if (e.which !== 13 || !this.input.val().trim()) { // ENTER_KEY = 13
            return;
        }
        app.todoList.create({// Save input in a collection
            title: this.input.val().trim(),
            type: this.inputType.val().trim(),
            length: this.inputLength.val().trim(),
            completed: false
        });
        this.input.val(''); // clean input box
        this.inputLength.val('');
    },

    addAll: function () {
        $('#output-field').text(''); // clean the output text
        this.$('#content-table').html(''); // clean the todo list
        app.todoList.each(function (todo) {
            var view = new app.TodoView({model: todo});
            $('#content-table').append(view.render().el);
            //$('#output-field').append(view.render().el);
            var obj = todo.attributes;
            console.log(obj.title + ' ' + obj.length);
            $('#output-field').append(this.lpad(obj.title, obj.length, obj.type === 'number' ? '0' : ' '));
        }, this);
    },

    lpad: function (str, minLen, ch) {
        return ((str.length < minLen)
                ? ((new Array(minLen - str.length + 1)).join(ch) + str) : str);
    }

});

//--------------
// Initializers
//--------------   

app.appView = new app.AppView();
