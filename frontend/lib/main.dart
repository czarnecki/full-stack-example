import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import 'common/auth.dart' as auth;
import 'common/config.dart' as config;
import 'widgets/auth_form.dart';
import 'widgets/home.dart';

main() async {
  var authenticated = await auth.authenticate();

  // Set default home based on result of authentication
  var defaultHome = authenticated ? Home() : AuthForm();

  // Initial setup for GraphQL client to work with in all views
  final AuthLink authLink = AuthLink(
    getToken: () async => 'Bearer ${await auth.fetchToken()}',
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
      theme: ThemeData(
        primarySwatch: Colors.teal,
      ),
      darkTheme: ThemeData.dark(),
      routes: {
        '/home': (BuildContext context) => Home(),
        '/login': (BuildContext context) => AuthForm(),
      },
    );
  }
}
