import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Component( {
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.less' ]
} )
export class AppComponent {

  public searchInput = '';
  public displayOverlay = false;
  public results: Array<{ url: string, tfidf: string }> = [];

  constructor( private http: HttpClient ) {
  }

  public search(): void {
    if ( this.searchInput.length > 0 ) {
      this.displayOverlay = true;

      this.http.get( environment.baseUrl + this.searchInput.replace( ' ', '+' ) ).subscribe( ( data: [ { url: string, tfidf: string } ] ) => {
        this.results = data;
        this.results.sort( ( f, s ) => {
          if ( parseFloat( f.tfidf ) > parseFloat( s.tfidf ) ) {
            return -1;
          } else if ( parseFloat( f.tfidf ) < parseFloat( s.tfidf ) ) {
            return 1;
          }
          return 0;
        } );
        this.displayOverlay = false;
      } );
    }
  }

  public round( value: number ) {
    return Math.round( value * 100 ) / 100;
  }

}
