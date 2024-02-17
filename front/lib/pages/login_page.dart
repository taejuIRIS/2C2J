import 'dart:async';
import 'dart:convert' show json;

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:http/http.dart' as http;

class LogInPage extends StatefulWidget {
  @override
  _LogInPageState createState() => _LogInPageState();
}

class _LogInPageState extends State<LogInPage> {
  GoogleSignIn _googleSignIn = GoogleSignIn(scopes: ['email', 'openid']);

  GoogleSignInAccount? _currentUser;
  bool _isAuthorized = false;
  String _tokenText = '';

  @override
  void initState() {
    super.initState();

    _googleSignIn.onCurrentUserChanged.listen((GoogleSignInAccount? account) async {
      bool isAuthorized = account != null;

      // On web, check if the user can access required scopes
      if (kIsWeb && account != null) {
        isAuthorized = await _googleSignIn.canAccessScopes(['email']);
      }

      setState(() {
        _currentUser = account;
        _isAuthorized = isAuthorized;
      });

      // Call the REST API if the user is authorized
      if (isAuthorized) {
        unawaited(_handleGetToken(account!));
      }
    });

    // On the web, signInSilently triggers the One Tap UX
    _googleSignIn.signInSilently();
  }

  Future<void> _handleGetToken(GoogleSignInAccount user) async {
    final GoogleSignInAuthentication googleSignInAuthentication = await user.authentication;
    final String accessToken = googleSignInAuthentication.accessToken!;

    setState(() {
      _tokenText = 'Access Token: $accessToken';
    });
    print('user id : ${user.id}');
  }

  Future<void> _handleSignIn() async {
    try {
      await _googleSignIn.signIn();
    } catch (error) {
      print(error);
    }
  }

  Future<void> _handleAuthorizeScopes() async {
    final bool isAuthorized = await _googleSignIn.requestScopes(['email']);

    setState(() {
      _isAuthorized = isAuthorized;
    });

    if (isAuthorized) {
      unawaited(_handleGetToken(_currentUser!));
    }
  }

  Future<void> _handleSignOut() => _googleSignIn.disconnect();

  Widget _buildBody() {
    final GoogleSignInAccount? user = _currentUser;

    if (user != null) {
      return Column(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: <Widget>[
          ListTile(
            leading: GoogleUserCircleAvatar(
              identity: user,
            ),
            title: Text(user.displayName ?? ''),
            subtitle: Text(user.email),
          ),
          const Text('Signed in successfully.'),
          if (_isAuthorized) ...<Widget>[
            Text(_tokenText),
            ElevatedButton(
              child: const Text('REFRESH TOKEN'),
              onPressed: () => _handleGetToken(user),
            ),
          ],
          if (!_isAuthorized) ...<Widget>[
            const Text('Additional permissions needed to read your contacts.'),
            ElevatedButton(
              onPressed: _handleAuthorizeScopes,
              child: const Text('REQUEST PERMISSIONS'),
            ),
          ],
          ElevatedButton(
            onPressed: _handleSignOut,
            child: const Text('SIGN OUT'),
          ),
        ],
      );
    } else {
      return Column(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: <Widget>[
          const Text('You are not currently signed in.'),
          ElevatedButton(
            onPressed: _handleSignIn,
            child: const Text('SIGN IN'),
          ),
        ],
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Google Sign In'),
      ),
      body: ConstrainedBox(
        constraints: const BoxConstraints.expand(),
        child: _buildBody(),
      ),
    );
  }
}

void unawaited(Future<void> future) {}
