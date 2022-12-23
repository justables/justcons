/** generated code */

import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { BASE_URL } from '../config';
import { IconDTO } from './dto/icon-dto';

@Injectable({
    providedIn: 'root',
})
export class IconService {
  constructor(protected http: HttpClient) {}

  delete(icon: IconDTO): Observable<any> {
    return this.http.post<any>(`${BASE_URL}/icon/delete`, icon)
  }

  getAll(): Observable<IconDTO[]> {
    return this.http.get<IconDTO[]>(`${BASE_URL}/icon`)
  }

  render(icon: IconDTO): Observable<IconDTO> {
    return this.http.post<IconDTO>(`${BASE_URL}/icon/render`, icon)
  }

  save(icon: IconDTO): Observable<IconDTO> {
    return this.http.post<IconDTO>(`${BASE_URL}/icon`, icon)
  }
}
