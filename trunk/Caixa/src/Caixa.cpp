using namespace std;
#include <iostream>
#include "header/Dispensador.h"
#include <string>


int main() {
	Dispensador* d = new Dispensador(2, 0, 7, 0, 0, 6);
	string s = "adasd";
	d->draw(672,s);
	cout << s;

	return 0;
}
