import 'config.dart' as config;
import 'secure_storage.dart';
import 'token.dart';

Future<bool> login() async {
  var username = await storage.read(key: 'username');
  var password = await storage.read(key: 'password');

  if (username == null || password == null) {
    return false;
  }

  config.username = username;
  config.password = password;

  var token = await fetchAuthToken();
  return token != null;
}
