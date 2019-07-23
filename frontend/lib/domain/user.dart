class User {
  final String username;
  final String givenName;
  final String familyName;

  get fullName => '$givenName $familyName';

  User._(this.username, this.givenName, this.familyName);

  static User fromQuery(dynamic data) {
    var username = data['user']['username'];
    var givenName = data['user']['givenName'];
    var familyName = data['user']['familyName'];
    return User._(username, givenName, familyName);
  }
}
