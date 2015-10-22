'use strict';

var React = require('react-native');

var RCTDeviceEventEmitter = require('RCTDeviceEventEmitter');
var StateX = require('react-native-statex');

var {
  AppRegistry
} = React;

var COUNT_KEY = "count";

var App = function() {
  RCTDeviceEventEmitter.addListener('add', async function add() {
    var count = await StateX.getItem(COUNT_KEY);
    var c = parseInt(count);
    if(Number.isNaN(c)) {
      c = 0;
    }
    c = c + 1;
    StateX.setItem(COUNT_KEY, c.toString());
  });
}

AppRegistry.registerRunnable('App', App);
