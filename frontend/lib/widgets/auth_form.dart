import 'package:flutter/material.dart';

import '../common/auth.dart' as auth;

/// Widget that provides form for credentials
class AuthForm extends StatelessWidget {
  final GlobalKey<FormState> _formKey = GlobalKey();
  final TextEditingController _username = TextEditingController();
  final TextEditingController _password = TextEditingController();

  InputDecoration _fieldDecoration(String hint) {
    return InputDecoration(
      hintText: hint,
      border: OutlineInputBorder(
        borderRadius: BorderRadius.circular(32.0),
      ),
    );
  }

  Widget _formField(
    String label,
    TextEditingController controller,
    bool obscure,
  ) {
    return Padding(
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
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Authenticate'),
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
                  _formField('Username', _username, false),
                  _formField('Password', _password, true),
                  ButtonTheme(
                    minWidth: 200,
                    child: FlatButton(
                      shape: StadiumBorder(),
                      onPressed: () {
                        _authenticate(context);
                      },
                      color: Theme.of(context).primaryColor,
                      textColor: Theme.of(context).textTheme.body1.color,
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

  /// Tries to authenticate the user with given username and password.
  ///
  /// If the authentication was successful the user is redirected to the home
  /// view. Otherwise a snackbar appears informing the user that something
  /// went wrong with the authentication.
  _authenticate(BuildContext context) async {
    if (_formKey.currentState.validate()) {
      var loggedIn = await auth.authenticate(_username.text, _password.text);
      if (loggedIn) {
        Navigator.pushReplacementNamed(context, '/home');
      } else {
        Scaffold.of(context).showSnackBar(
          SnackBar(
            content: Text('Error authenticating'),
            action: SnackBarAction(
              label: 'Close',
              onPressed: () => Scaffold.of(context).removeCurrentSnackBar(),
            ),
          ),
        );
      }
    }
  }
}
