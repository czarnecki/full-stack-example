import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import 'common/auth.dart' as auth;
import 'common/config.dart' as config;
import 'common/token.dart';
import 'widgets/home.dart';
import 'widgets/login.dart';

main() async {
  var loggedIn = await auth.login();
  var defaultHome = loggedIn ? Home() : Login();

  final AuthLink authLink = AuthLink(
    getToken: () async => 'Bearer ${await fetchAuthToken()}',
  );
  final HttpLink httpLink = HttpLink(
    uri: config.graphQlApiUri,
  );
  final Link link = authLink.concat(httpLink);
  final ValueNotifier<GraphQLClient> client = ValueNotifier(
    GraphQLClient(
      link: link,
      cache: InMemoryCache(),
    ),
  );

  runApp(
    GraphQLProvider(
      client: client,
      child: App(defaultHome),
    ),
  );
}

class App extends StatelessWidget {
  final Widget _defaultHome;

  App(this._defaultHome);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FSE Frontend App',
      home: _defaultHome,
      routes: {
        '/home': (BuildContext context) => Home(),
        '/login': (BuildContext context) => Login(),
      },
    );
  }
}
