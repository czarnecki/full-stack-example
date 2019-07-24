import 'package:flutter/material.dart';

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

  static final _fieldDecoration = (String hint) => InputDecoration(
        hintText: hint,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(32.0),
        ),
      );

  static final _loginField =
      (String label, TextEditingController controller, bool obscure) => Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextFormField(
              controller: controller,
              validator: (value) {
                return value.isEmpty ? '$label cannot be empty' : null;
              },
              decoration: _fieldDecoration(label),
              obscureText: obscure,
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
                  _loginField('Username', _username, false),
                  _loginField('Password', _password, true),
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
      var loggedIn = await auth.login(_username.text, _password.text);
      if (loggedIn) {
        Navigator.pushReplacementNamed(context, '/home');
      } else {
        Scaffold.of(context).showSnackBar(
          SnackBar(
            content: Text('Error logging in'),
            action: SnackBarAction(
                label: 'Close',
                onPressed: () => Scaffold.of(context).removeCurrentSnackBar()),
          ),
        );
      }
    }
  }
}
