import 'dart:convert' as convert;

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;

import 'config.dart' as config;

final FlutterSecureStorage _storage = FlutterSecureStorage();

void _writeCredentials(String username, String password) {
  _storage.write(key: 'username', value: username);
  _storage.write(key: 'password', value: password);
}

Future<Map<String, String>> _readCredentials() async {
  var username = await _storage.read(key: 'username');
  var password = await _storage.read(key: 'password');
  return {'username': username, 'password': password};
}

/// Fetches token from Keycloak Server
///
/// Returns a JWT authentication token when a valid username and password are
/// found in the secure storage otherwise it returns null.
Future<String> fetchToken() async {
  var credentials = await _readCredentials();
  var response = await http.post(
    config.keycloakUri,
    body: {
      'grant_type': config.grantType,
      'client_id': config.clientId,
      'username': credentials['username'] ?? '',
      'password': credentials['password'] ?? '',
    },
  );
  return convert.jsonDecode(response.body)['access_token'];
}

/// Tries to authenticate the user
///
/// Returns true if the user could be authenticated successfully and false
/// otherwise.
/// [username] and [password] have to be either both null or both given.
Future<bool> authenticate([String username, String password]) async {
  assert(
    (username == null && password == null) ||
        (username != null && password != null),
    'Either no or full credentials have to be given',
  );

  if (username != null && password != null) {
    _writeCredentials(username, password);
  }

  var token = await fetchToken();
  return token != null;
}
