import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { appHttpInterceptor } from './interceptors/app-http.interceptor';
import { CommonModule } from '@angular/common';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    ReactiveFormsModule,
    provideHttpClient(withInterceptors([appHttpInterceptor])),
    CommonModule,
  ],
};
