 var express = require('express');
 var app = express();
 var ejs = require('ejs');
 var http = require('http').Server(app); //1
 var io = require('socket.io')(http);

 app.set('view engine', 'html');
 app.engine('html', ejs.renderFile);
 app.get('/', function (req, res) {
   res.render('post.html');
 })

io.sockets.on('connection', function (socket) {
  console.log('user connected: ', socket.id);

  socket.on('message', function (text) {
    var msg = text;

    io.sockets.emit('receiveMsg', msg);
  });
});

io.sockets.on('disconnect', function () { //3-2
    console.log('user disconnected: ', socket.ID);
});

http.listen(9000, function () {
   console.log('server on!');
});