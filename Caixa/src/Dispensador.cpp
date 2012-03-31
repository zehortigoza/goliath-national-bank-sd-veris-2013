/*
 * Dispensador.cpp
 *
 *  Created on: Mar 27, 2012
 *      Author: zehortigoza
 */

#include "header/Dispensador.h"

Dispensador::Dispensador() {
	// TODO Auto-generated constructor stub

}

Dispensador::~Dispensador() {
	// TODO Auto-generated destructor stub
}

Dispensador::Dispensador(int amount2, int amount5, int amount10, int amount20, int amount50, int amount100) {
	this->Amount2 = amount2;
	this->Amount5 = amount5;
	this->Amount10 = amount10;
	this->Amount20 = amount20;
	this->Amount50 = amount50;
	this->Amount100 = amount100;
}

bool Dispensador::draw(double value, string &msg) {
	int resultDivision = 0;
	double rest = value;

	int widraw100 = 0;
	int widraw50 = 0;
	int widraw20 = 0;
	int widraw10 = 0;
	int widraw5 = 0;
	int widraw2 = 0;

	resultDivision = rest/100;
	if(resultDivision > 0) {
		if(Amount100 >= resultDivision) {
			widraw100 = resultDivision;
			rest = value - (resultDivision * 100);
		} else {
			widraw100 = Amount100;
			rest = rest - (100 * Amount100);
		}
	}

	resultDivision = rest/50;
	if(resultDivision > 0) {
		if(Amount50 >= resultDivision) {
			widraw50 = resultDivision;
			rest = rest - (resultDivision * 50);
		} else {
			widraw50 = Amount50;
			rest = rest - (50 * Amount50);
		}
	}

	resultDivision = rest/20;
	if(resultDivision > 0) {
		if(Amount20 >= resultDivision) {
			widraw20 = resultDivision;
			rest = rest - (resultDivision * 20);
		} else {
			widraw20 = Amount20;
			rest = rest - (20 * Amount20);
		}
	}

	resultDivision = rest/10;
	if(resultDivision > 0) {
		if(Amount10 >= resultDivision) {
			widraw10 = resultDivision;
			rest = rest - (resultDivision * 10);
		} else {
			widraw10 = Amount10;
			rest = rest - (10 * Amount10);
		}
	}

	resultDivision = rest/5;
	if(resultDivision > 0) {
		if(Amount5 >= resultDivision) {
			widraw5 = resultDivision;
			rest = rest - (resultDivision * 5);
		} else {
			widraw5 = Amount5;
			rest = rest - (5 * Amount5);
		}
	}

	resultDivision = rest/2;
	if(resultDivision > 0) {
		if(Amount2 >= resultDivision) {
			widraw2 = resultDivision;
			rest = rest - (resultDivision * 2);
		} else {
			widraw2 = Amount2;
			rest = rest - (2 * Amount2);
		}
	}

	if(rest > 0) {
		if(rest >= 2) {
			//msg = new String("asfdsa");
			msg = "Não existem notas sulficientes para realizar esse saque";
			return false;
		}
		else {
			msg = "Valor inválido para saque";
			return false;
		}
	} else {
		Amount100 -= widraw100;
		Amount50 -= widraw50;
		Amount20 -= widraw20;
		Amount10 -= widraw10;
		Amount5 -= widraw5;
		Amount2 -= widraw2;

		stringstream ss;

		if(widraw100 > 0) {
			ss << "[" << widraw100 << "] notas de 100 - ";
		}

		if(widraw50 > 0) {
			ss << "[" << widraw50 << "] notas de 50 - ";
		}

		if(widraw20 > 0) {
			ss << "[" << widraw20 << "] notas de 20 - ";
		}

		if(widraw10 > 0) {
			ss << "[" << widraw10 << "] notas de 10 - ";
		}

		if(widraw5 > 0) {
			ss << "[" << widraw5 << "] notas de 5 - ";
		}

		if(widraw2 > 0) {
			ss << "[" << widraw2 << "] notas de 2";
		}

		msg = ss.str();

		return true;
	}
}
