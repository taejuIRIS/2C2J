class RoutineLog {
  int? id;
  int? num;
  int? complete;
  DateTime? date;

  RoutineLog({
    this.id,
    this.num,
    this.complete,
    this.date,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'num': num,
      'complete': complete,
      'date': date?.toIso8601String(),
    };
  }

  factory RoutineLog.fromMap(Map<String, dynamic> map) {
    return RoutineLog(
      id: map['id'],
      num: map['num'],
      complete: map['complete'],
      date: map['date'] != null ? DateTime.parse(map['date']) : null,
    );
  }
}
