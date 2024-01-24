class Routine {
  int? num;
  String title;
  String content;
  String memo;
  DateTime startDate;
  DateTime endDate;
  String doType;
  int selectedDays;
  String userId;
  bool complete;

  Routine({
    this.num,
    required this.title,
    required this.content,
    required this.memo,
    required this.startDate,
    required this.endDate,
    required this.doType,
    required this.selectedDays,
    required this.userId,
    this.complete = false,
  });

  Map<String, dynamic> toMap() {
    return {
      'num': num,
      'title': title,
      'content': content,
      'memo': memo,
      'start_date': startDate.toIso8601String(),
      'end_date': endDate.toIso8601String(),
      'do_type': doType,
      'selected_days': selectedDays,
      'user_id': userId,
      'complete': complete ? 1 : 0,
    };
  }

  factory Routine.fromMap(Map<String, dynamic> map) {
    return Routine(
      num: map['num'],
      title: map['title'],
      content: map['content'],
      memo: map['memo'],
      startDate: DateTime.parse(map['start_date']),
      endDate: DateTime.parse(map['end_date']),
      doType: map['do_type'],
      selectedDays: map['selected_days'],
      userId: map['user_id'],
      complete: map['complete'] == 1,
    );
  }
}