import 'package:intl/intl.dart';
import 'package:sqflite/sqflite.dart';
import 'package:todotodo/database/routine.dart';
import 'package:path/path.dart';
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
    print("_initDatabase");
    return openDatabase(
      path,
      version: 1,
      onCreate: _createDatabase,
    );
  }

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

}