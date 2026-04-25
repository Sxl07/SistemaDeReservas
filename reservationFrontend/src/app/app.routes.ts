import { Routes } from '@angular/router';

import { ReservationTableComponent } from './components/reservation-table/reservation-table.component';
import { ReservationFormComponent } from './components/reservation-form/reservation-form.component';

export const routes: Routes = [
	{
		path: '',
		pathMatch: 'full',
		redirectTo: 'reservations',
	},
	{
		path: 'reservations',
		component: ReservationTableComponent,
	},
	{
		path: 'reservations/new',
		component: ReservationFormComponent,
	},
	{
		path: '**',
		redirectTo: 'reservations',
	},
];
