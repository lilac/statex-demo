/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var StateX = require('react-native-statex');

var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} = React;

var KEY = "count";

var App = React.createClass({
  componentDidMount() {
    this._loadInitialState().done();
  },

  async _loadInitialState() {
    try {
      var value = await StateX.getItem(KEY);
      if (value !== null){
        this.setState({count: value});
        this._appendMessage('Recovered count from disk: ' + value);
      } else {
        this._appendMessage('Initialized with no count value on disk.');
      }
    } catch (error) {
      this._appendMessage('StateX.getItem error: ' + error.message);
    }
  },

  getInitialState() {
    return {
      count: 0,
      messages: [],
    };
  },

  render: function() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Shake or press menu button for dev menu
        </Text>
        <Text>
          {'Count: '}
          <Text style={{color: "red"}}>
            {this.state.count}
          </Text>
        </Text>
        <Text>{' '}</Text>
        <Text onPress={this._increment}>
          Increment (+1)
        </Text>
        <Text>{' '}</Text>
        <Text>Messages:</Text>
        {this.state.messages.map((m) => <Text>{m}</Text>)}
      </View>
    );
  },

  _increment() {
    this._onValueChange(this.state.count + 1);
  },

  async _onValueChange(count) {
    this.setState({count});
    try {
      await StateX.setItem(KEY, count.toString());
      this._appendMessage('Saved count to disk: ' + count);
    } catch (error) {
      this._appendMessage('StateX.setItem error: ' + error.message);
    }
  },

  _appendMessage(message) {
    this.setState({messages: this.state.messages.concat(message)});
  }
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('StateX', () => App);
