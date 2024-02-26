import 'package:flutter/material.dart';
import 'package:todotodo/my_style.dart';
import 'package:todotodo/widget/add_routine/add_routine_date_card.dart';
import 'package:todotodo/widget/add_routine/add_routine_dotype_card.dart';
import 'package:todotodo/widget/add_routine/add_routine_name_card.dart';
import 'package:todotodo/widget/bottom_btn.dart';
import 'package:todotodo/widget/sliver_padding_box.dart';
import 'package:todotodo/widget/sliver_spacer.dart';

class AddRoutinePage extends StatefulWidget {
  AddRoutinePage({Key? key}) : super(key: key);

  @override
  _AddRoutinePageState createState() => _AddRoutinePageState();
}

class _AddRoutinePageState extends State<AddRoutinePage> {
  late TextEditingController _textEditingController;
  late String _routineName;
  late DateTime _startDate;

  @override
  void initState() {
    super.initState();
    _textEditingController = TextEditingController();
    _routineName = "";
    _startDate = DateTime.now();
  }

  @override
  void dispose() {
    _textEditingController.dispose();
    super.dispose();
  }

  void _onTextChange(String value) {
    setState(() {
      _routineName = value;
    });
  }

  void _onStartDateChange(DateTime value) {
    setState(() {
      _startDate = value;
    });
  }

  void _saveRoutine() {
    if (_routineName.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('루틴 이름을 입력해주세요.'),
        ),
      );
    } else {
      print('루틴 이름: $_routineName');
      print('시작일: $_startDate');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: MyColors.beige,
      body: Stack(
        children: [
          CustomScrollView(
            slivers: [
              SliverAppBar(
                backgroundColor: MyColors.beige,
                leading: IconButton(
                  icon: Icon(Icons.arrow_back, color: Colors.black),
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                ),
              ),
              SliverPaddingBox(
                child: AddRoutineNameCard(
                  textEditingController: _textEditingController,
                  onChanged: _onTextChange,
                ),
              ),
              SliverSpacer(),
              SliverPaddingBox(
                child: AddRoutineDateCard(
                  headText: '🗓️ 시작일',
                  onChanged: _onStartDateChange,
                ),
              ),
              SliverSpacer(),
            ],
          ),
          BottomButton(
            mainColor: MyColors.sky_blue,
            onPressed: _saveRoutine, // 저장 버튼 클릭 시 _saveRoutine 함수 호출
            text: '저장',
          )
        ],
      ),
    );
  }
}
