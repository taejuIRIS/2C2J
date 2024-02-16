import 'package:intl/intl.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:todotodo/database/routine.dart';
import 'package:todotodo/database/routine_log.dart';

class RoutineDBProvider {
  // 싱글톤 인스턴스
  static final RoutineDBProvider _instance = RoutineDBProvider._privateConstructor();
  static Database? _database; // 데이터베이스 인스턴스

  RoutineDBProvider._privateConstructor();

  factory RoutineDBProvider() {
    return _instance;
  }

  // 데이터베이스 인스턴스를 얻는 메서드
  Future<Database> get database async => _database ??= await initDB();

  // 데이터베이스 초기화 메서드
  Future initDB() async {
    String path = join(await getDatabasesPath(), 'routines.db');
    print(path);
    _database = await openDatabase(
      path,
      version: 3,
      onCreate: _createDatabase,
    );
    return _database!;
  }

  // 데이터베이스 테이블 생성 및 초기 데이터 삽입
  Future<void> _createDatabase(Database db, int version) async {
    await db.execute('''
      CREATE TABLE routines(
        num INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        content TEXT,
        memo TEXT,
        start_date TEXT,
        end_date TEXT,
        do_type TEXT,
        selected_days INTEGER,
        user_id TEXT,
        complete INTEGER
      )
    ''');

    await db.execute('''
      CREATE TABLE routine_logs(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        num INTEGER,
        complete INTEGER,
        date TEXT,
        FOREIGN KEY (num) REFERENCES routines (num)
      )
    ''');

    if (await _isDatabaseEmpty(db)) {
      await _insertSampleData(db);
    }
  }

  // 데이터베이스가 비어있는지 확인
  Future<bool> _isDatabaseEmpty(Database db) async {
    List<Map<String, dynamic>> result = await db.rawQuery('SELECT COUNT(*) as count FROM routines');
    int? count = Sqflite.firstIntValue(result);
    return count == 0;
  }

  // 샘플 데이터 삽입
  Future<void> _insertSampleData(Database db) async {
    List<Routine> sampleRoutines = [
      Routine(
        title: '운동하기',
        content: '체육관에서 1시간 운동',
        memo: '헬스, 유산소',
        startDate: DateTime.now(),
        endDate: DateTime.now().add(Duration(days: 30)),
        doType: '운동',
        selectedDays: 127, // 매일
        userId: 'user1',
        complete: 0,
      ),
      Routine(
        title: '독서하기',
        content: '책 1권 읽기',
        memo: '소설, 자기계발서 등',
        startDate: DateTime.now(),
        endDate: DateTime.now().add(Duration(days: 30)),
        doType: '독서',
        selectedDays: 62, // 월, 수, 금
        userId: 'user1',
        complete: 0,
      ),
    ];

    for (Routine routine in sampleRoutines) {
      await db.insert('routines', routine.toMap());
    }
  }

  // 루틴 추가 메서드
  Future<int> insertRoutine(Routine routine) async {
    Database db = await database;
    return await db.insert('routines', routine.toMap());
  }

  // 모든 루틴 가져오기
  Future<List<Routine>> getRoutines() async {
    Database db = await database;
    List<Map<String, dynamic>> maps = await db.query('routines');
    return List.generate(maps.length, (i) {
      return Routine.fromMap(maps[i]);
    });
  }

  // 루틴 업데이트 메서드
  Future<int> updateRoutine(Routine routine) async {
    Database db = await database;
    // if (인터넷 연결 == true){
    //   api
    //   pk - user.id
    // }
    return await db.update('routines', routine.toMap(), where: 'num = ?', whereArgs: [routine.num]);
  }

  // 루틴 삭제 메서드
  Future<int> deleteRoutine(int num) async {
    Database db = await database;
    return await db.delete('routines', where: 'num = ?', whereArgs: [num]);
  }

  // 특정 날짜의 루틴 가져오기
  Future<List<Routine>> getDailyRoutines(DateTime dateTime) async {
    Database db = await database;
    String formattedDate = DateFormat('yyyy-MM-dd').format(dateTime);
    print(formattedDate);
    List<Map<String, dynamic>> maps = await db.query(
      'routines',
      where: 'date(start_date) <= date(?) AND date(end_date) >= date(?)',
      whereArgs: [formattedDate, formattedDate],
    );
    return List.generate(maps.length, (i) {
      return Routine.fromMap(maps[i]);
    });
  }

  // 가장 최근 로그의 날짜 가져오는 메서드
  Future<DateTime> getLastLogDateTime(int routine_num) async {
    Database db = await database;
    List<Map<String, dynamic>> result = await db.rawQuery(
      'SELECT MAX(date) as lastLogDay FROM routine_logs WHERE num = ?',
      [routine_num],
    );

    String? lastLogDayString = result.first['lastLogDay'];
    if (lastLogDayString != null) {
      print("LastLog $routine_num $lastLogDayString");
      return DateTime.parse(lastLogDayString);
    } else {
      return DateTime.now().subtract(Duration(days: 1));
    }
  }

  // 오늘까지 로그 삽입 메서드
  Future<void> insertLogUntilToday(int routine_num) async {
    Database db = await database;
    DateTime lastLogDateTime = await getLastLogDateTime(routine_num);
    DateTime today = DateTime.now();

    while (!isSameDay(lastLogDateTime, today)) {
      lastLogDateTime = lastLogDateTime.add(Duration(days: 1));
      RoutineLog log = RoutineLog(
        num: routine_num,
        complete: 0,
        date: lastLogDateTime,
      );
      print("Insert $routine_num");
      await db.insert('routine_logs', log.toMap());
    }
  }

  // 루틴 로그 가져오기 메서드
  Future<List<RoutineLog>> getRoutineLogs(int routine_num) async {
    Database db = await database;
    List<Map<String, dynamic>> maps = await db.query(
      'routine_logs',
      where: 'num = ?',
      whereArgs: [routine_num],
    );
    return List.generate(maps.length, (i) {
      return RoutineLog.fromMap(maps[i]);
    });
  }

  // 두 날짜가 같은지 확인하는 메서드
  bool isSameDay(DateTime date1, DateTime date2) {
    return date1.year == date2.year && date1.month == date2.month && date1.day == date2.day;
  }
}
