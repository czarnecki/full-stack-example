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

  final fieldDecoration = (String hint) => InputDecoration(
        hintText: hint,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(32.0),
        ),
      );

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
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextFormField(
                      controller: _username,
                      validator: (value) {
                        return value.isEmpty
                            ? 'Username cannot be empty'
                            : null;
                      },
                      decoration: fieldDecoration('Username'),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextFormField(
                      controller: _password,
                      obscureText: true,
                      validator: (value) {
                        return value.isEmpty
                            ? 'Password cannot be empty'
                            : null;
                      },
                      decoration: fieldDecoration('Password'),
                    ),
                  ),
                  ButtonTheme(
                    minWidth: 200,
                    child: FlatButton(
                      shape: StadiumBorder(),
                      onPressed: () {
                        _login(context);
                      },
                      color: Theme.of(context).primaryColor,
                      textColor: Theme.of(context).secondaryHeaderColor,
                      child: Text('Login'),
                    ),
                  )
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  _login(BuildContext context) async {
    if (_formKey.currentState.validate()) {
      await storage.write(key: 'username', value: _username.text);
      await storage.write(key: 'password', value: _password.text);

      var loggedIn = await auth.login();
      if (loggedIn) {
        Navigator.pushReplacementNamed(context, '/home');
      } else {
        Scaffold.of(context).showSnackBar(
          SnackBar(
            content: Text('Error loggin in'),
            action: SnackBarAction(
                label: 'Close',
                onPressed: () => Scaffold.of(context).removeCurrentSnackBar()),
          ),
        );
      }
    }
  }
}
