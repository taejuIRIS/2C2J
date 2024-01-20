import 'package:intl/intl.dart';
import 'package:sqflite/sqflite.dart';
import 'package:todotodo/database/routine.dart';
import 'package:path/path.dart';
import 'package:todotodo/database/routine_log.dart';
class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._privateConstructor();
  static Future<Database>? _database;

  DatabaseHelper._privateConstructor();

  Future<Database> get database async {
    if (_database == null) {
      await init();
    }
    return _database!;
  }
  Future<void> init() async {
    _database = _initDatabase();
    await _database;
  }
  Future<Database> _initDatabase() async {
    String path = join(await getDatabasesPath(), 'routines.db');
    print(path);
    print("_initDatabase");
    Database database = await openDatabase(
      path,
      version: 2,
      onCreate: _createDatabase,
    );
    print('Database version: ${await database.getVersion()}'); // 현재 데이터베이스 버전 출력
    return database;
  }


  Future<void> _createDatabase(Database db, int version) async {
    print("create db");
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
    print("create 1");
    await db.execute('''
      CREATE TABLE routine_logs(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        num INTEGER,
        complete INTEGER,
        date TEXT,
        FOREIGN KEY (num) REFERENCES routines (num)
      )
    ''');
    print("create 2");
    if (await _isDatabaseEmpty(db)) {
      await _insertSampleData(db);
    }
  }
  Future<bool> _isDatabaseEmpty(Database db) async {
    List<Map<String, dynamic>> result = await db.rawQuery('SELECT COUNT(*) as count FROM routines');
    int? count = Sqflite.firstIntValue(result);
    return count == 0;
  }
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
        complete: false,
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
        complete: false,
      ),
    ];

    for (Routine routine in sampleRoutines) {
      await db.insert('routines', routine.toMap());
    }
  }
  Future<int> insertRoutine(Routine routine) async {
    Database db = await database;
    return await db.insert('routines', routine.toMap());
  }

  Future<List<Routine>> getRoutines() async {
    print("getR");
    Database db = await database;
    List<Map<String, dynamic>> maps = await db.query('routines');
    return List.generate(maps.length, (i) {
      return Routine.fromMap(maps[i]);
    });
  }

  Future<int> updateRoutine(Routine routine) async {
    Database db = await database;
    return await db.update('routines', routine.toMap(), where: 'num = ?', whereArgs: [routine.num]);
  }

  Future<int> deleteRoutine(int num) async {
    Database db = await database;
    return await db.delete('routines', where: 'num = ?', whereArgs: [num]);
  }

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

  Future<DateTime> getLastLogDay(int routine_num) async {
    Database db = await database;
    List<Map<String, dynamic>> result = await db.rawQuery(
      'SELECT MAX(date) as lastLogDay FROM routine_logs WHERE num = ?',
      [routine_num],
    );

    String? lastLogDayString = result.first['lastLogDay'];
    if (lastLogDayString != null) {
      print("LastLog ${routine_num} ${lastLogDayString}");
      return DateTime.parse(lastLogDayString);
    } else {
      // 루틴에 대한 기록이 아직 없는 경우, 기본값 반환하거나 상황에 맞게 처리
      return DateTime.now().subtract(Duration(days: 1));
    }
  }

  Future<void> insertLogUntilToday(int routine_num) async {
    Database db = await database;
    DateTime lastLogDay = await getLastLogDay(routine_num);
    DateTime today = DateTime.now();

    // 로그를 삽입해야 하는지 확인
    while (!isSameDay(lastLogDay, today)) {
      lastLogDay = lastLogDay.add(Duration(days: 1));

      RoutineLog log = RoutineLog(
        num: routine_num,
        complete: 0, // 0 또는 기본값으로 설정
        date: lastLogDay,
      );
      print("insert ${routine_num}");
      await db.insert('routine_logs', log.toMap());
    }
  }
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

  bool isSameDay(DateTime date1, DateTime date2) {
    return date1.year == date2.year && date1.month == date2.month && date1.day == date2.day;
  }


}