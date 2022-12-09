/** generated code */

import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { BASE_URL } from '../config';
import { CatDTO } from './/cat.dto';

@Injectable({
  providedIn: 'root',
})
export class CatController {
  constructor(protected http: HttpClient) {}

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${BASE_URL}/cat/${id}`);
  }

  get(id: number): Observable<CatDTO> {
    return this.http.get<CatDTO>(`${BASE_URL}/cat/${id}`);
  }

  getAll(): Observable<CatDTO[]> {
    return this.http.get<CatDTO[]>(`${BASE_URL}/cats`);
  }

  post(cat: CatDTO): Observable<CatDTO> {
    return this.http.post<CatDTO>(`${BASE_URL}/cat`, cat);
  }
}
