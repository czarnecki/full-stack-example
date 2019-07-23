import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import 'common/config.dart' as config;
import 'common/token.dart';
import 'widgets/home.dart';

main() async {
  final AuthLink authLink =
      AuthLink(getToken: () async => 'Bearer ${await fetchAuthToken()}');
  final HttpLink httpLink = HttpLink(uri: config.graphQlApiUri);
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
      child: App(),
    ),
  );
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) => MaterialApp(
        title: 'FSE Frontend App',
        home: Home(),
      );
}
