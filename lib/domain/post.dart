class Post {
  final int id;
  final String message;
  final DateTime creationDate;

  Post._(this.id, this.message, this.creationDate);

  static Post fromQuery(dynamic data) {
    var id = data['post']['id'];
    var message = data['post']['message'];
    var creationDate = DateTime.parse(data['post']['creationDate']);
    return Post._(id, message, creationDate);
  }
}
