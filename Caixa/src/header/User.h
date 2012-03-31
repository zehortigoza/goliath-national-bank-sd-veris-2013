/*
 * User.h
 *
 *  Created on: Mar 27, 2012
 *      Author: zehortigoza
 */

#ifndef USER_H_
#define USER_H_

#include <string>
using namespace std;

class User {
private:
	string name;
	int key;
public:
	User();
	virtual ~User();
	int getKey();
	string getName();
	void setKey(int key);
	void setName(string name);
};

#endif /* USER_H_ */
