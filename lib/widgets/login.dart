import 'package:flutter/material.dart';
import 'package:frontend/common/secure_storage.dart';

import '../common/auth.dart' as auth;

class Login extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _LoginState();
  }
}

class _LoginState extends State<Login> {
  final GlobalKey<FormState> _formKey = GlobalKey();
  final TextEditingController _username = TextEditingController();
  final TextEditingController _password = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Login'),
      ),
      body: Builder(
        builder: (BuildContext context) {
          return Form(
            key: _formKey,
            child: Column(
              children: <Widget>[
                TextFormField(
                  controller: _username,
                  validator: (value) {
                    if (value.isEmpty) {
                      return 'Can\'t be empty';
                    }
                    return null;
                  },
                ),
                TextFormField(
                  controller: _password,
                  obscureText: true,
                  validator: (value) {
                    return value.isEmpty ? 'Cannot be empty' : null;
                  },
                ),
                RaisedButton(
                  onPressed: () {
                    _login();
                  },
                  child: Text('Login'),
                )
              ],
            ),
          );
        },
      ),
    );
  }

  _login() async {
    if (_formKey.currentState.validate()) {
      await storage.write(key: 'username', value: _username.text);
      await storage.write(key: 'password', value: _password.text);
    }
    var loggedIn = await auth.login();
    if (loggedIn) {
      Navigator.pushReplacementNamed(context, '/home');
    }
  }
}
