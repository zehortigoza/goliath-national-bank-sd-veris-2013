/*
 * User.cpp
 *
 *  Created on: Mar 27, 2012
 *      Author: zehortigoza
 */

#include "header/User.h"

User::User() {
	// TODO Auto-generated constructor stub

}

User::~User() {
	// TODO Auto-generated destructor stub
}

int User::getKey() {
	return key;
}

string User::getName() {
	return name;
}

void User::setKey(int key) {
	this->key = key;
}

void User::setName(string name) {
	this->name = name;
}
