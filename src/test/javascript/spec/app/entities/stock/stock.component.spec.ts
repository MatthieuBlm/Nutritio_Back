/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { NutritioBackTestModule } from '../../../test.module';
import { StockComponent } from '../../../../../../main/webapp/app/entities/stock/stock.component';
import { StockService } from '../../../../../../main/webapp/app/entities/stock/stock.service';
import { Stock } from '../../../../../../main/webapp/app/entities/stock/stock.model';

describe('Component Tests', () => {

    describe('Stock Management Component', () => {
        let comp: StockComponent;
        let fixture: ComponentFixture<StockComponent>;
        let service: StockService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [StockComponent],
                providers: [
                    StockService
                ]
            })
            .overrideTemplate(StockComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StockComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Stock(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.stocks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
