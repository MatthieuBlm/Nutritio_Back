/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { NutritioBackTestModule } from '../../../test.module';
import { GrocerieComponent } from '../../../../../../main/webapp/app/entities/grocerie/grocerie.component';
import { GrocerieService } from '../../../../../../main/webapp/app/entities/grocerie/grocerie.service';
import { Grocerie } from '../../../../../../main/webapp/app/entities/grocerie/grocerie.model';

describe('Component Tests', () => {

    describe('Grocerie Management Component', () => {
        let comp: GrocerieComponent;
        let fixture: ComponentFixture<GrocerieComponent>;
        let service: GrocerieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [GrocerieComponent],
                providers: [
                    GrocerieService
                ]
            })
            .overrideTemplate(GrocerieComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrocerieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrocerieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Grocerie(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.groceries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
