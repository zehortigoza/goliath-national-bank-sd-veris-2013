/*
 * Dispensador.h
 *
 *  Created on: Mar 27, 2012
 *      Author: zehortigoza
 */

#ifndef DISPENSADOR_H_
#define DISPENSADOR_H_

#include <string>
#include <sstream>
using namespace std;


class Dispensador {
private:
	int Amount2;
	int Amount5;
	int Amount10;
	int Amount20;
	int Amount50;
	int Amount100;
public:
	Dispensador();
	Dispensador(int amount2, int amount5, int amount10, int amount20, int amount50, int amount100);
	virtual ~Dispensador();
	bool draw(double value, string &msg);
};

#endif /* DISPENSADOR_H_ */
