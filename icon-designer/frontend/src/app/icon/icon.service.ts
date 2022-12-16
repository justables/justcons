/** generated code */

import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { BASE_URL } from '../config';
import { IconDTO } from './dto/icon-dto';
import { IconStackDTO } from './dto/icon-stack-dto';

@Injectable({
    providedIn: 'root',
})
export class IconService {
  constructor(protected http: HttpClient) {}

  getAll(): Observable<IconDTO[]> {
    return this.http.get<IconDTO[]>(`${BASE_URL}/icon`)
  }

  renderStack(iconStack: IconStackDTO): Observable<string> {
    return this.http.post<string>(`${BASE_URL}/icon/render-stack`, iconStack)
  }
}
