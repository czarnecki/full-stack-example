import 'dart:convert' as convert;

import 'package:http/http.dart' as http;

import 'config.dart' as config;

Future<String> fetchAuthToken() async {
  var response = await http.post(
    config.keycloakUri,
    body: {
      'grant_type': config.grantType,
      'client_id': config.clientId,
      'username': config.username,
      'password': config.password,
    },
  );
  return convert.jsonDecode(response.body)['access_token'];
}
